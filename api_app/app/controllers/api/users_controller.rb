require 'pry'
module Api
  class UsersController < ApplicationController
    skip_before_action :authenticate_request, :only => :create
    before_action :set_user, only: [:show, :edit, :update, :destroy]

    # GET /users
    # GET /users.json
    def index
      @users = User.all
      render json: @users
    end

    # GET /users/1
    # GET /users/1.json
    def show
      @user = User.find_by(user_id: params[:id])
      render 'show', formats: 'json', handlers: 'jbuilder'
    end

    # GET /users/1/edit
    def edit
    end

    # POST /users
    # POST /users.json
    def create
      @user = User.new(user_params)
      @user.save
      render 'user', formats: 'json', handlers: 'jbuilder' unless @user.errors.present?
      head 422 if @user.errors.present?
    end

    # PATCH/PUT /users/1
    # PATCH/PUT /users/1.json
    # def update
    #   respond_to do |format|
    #     if @user.update(user_params)
    #       format.html { redirect_to @user, notice: 'User was successfully updated.' }
    #       format.json { render :show, status: :ok, location: @user }
    #     else
    #       format.html { render :edit }
    #       format.json { render json: @user.errors, status: :unprocessable_entity }
    #     end
    #   end
    # end

    # DELETE /users/1
    # DELETE /users/1.json
    # def destroy
    #   @user.destroy
    #   respond_to do |format|
    #     format.html { redirect_to users_url, notice: 'User was successfully destroyed.' }
    #     format.json { head :no_content }
    #   end
    # end

    def get_own_postings
      @user = current_user
      @user = User.find(params[:id]) unless params[:id].nil?
      @user.postings
      render 'get_own_postings', formats: 'json', handlers: 'jbuilder'
    end

    def like_user_id_query
      @action = "6"
      @following = current_user.following.select("id")
      @users = User.where("id NOT IN (?) AND id NOT IN (?)", @following, current_user.id) if params[:user_id].empty?
      @users = User.where("user_id LIKE ? AND id NOT IN (?) AND id NOT IN (?)",
       "%#{params[:user_id]}%", current_user.id, @following) unless params[:user_id].empty?
      render 'users', formats: 'json', handlers: 'jbuilder'
    end

    private
    # Use callbacks to share common setup or constraints between actions.
    def set_user
      @user = User.find_by(user_id: params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def user_params
      params.require(:user).permit(:user_id, :email, :password_confirmation, :password, :icon, :icon_content_type, :fb_account)
    end
  end
end
