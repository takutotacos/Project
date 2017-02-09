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

    # # GET /users/new
    # def new
    #   @user = User.new
    # end

    # GET /users/1/edit
    def edit
    end

    # POST /users
    # POST /users.json
    def create
      @user = User.new(user_params)
      @action = "CREATE"
      if @user.save
        @status = 2
        render json: { action: @action, status: @status, user: @user }
      else
        @status = 3
        render json: { action: @action, status: @status, errors: @user.errors }
      end
    end

    # PATCH/PUT /users/1
    # PATCH/PUT /users/1.json
    def update
      respond_to do |format|
        if @user.update(user_params)
          format.html { redirect_to @user, notice: 'User was successfully updated.' }
          format.json { render :show, status: :ok, location: @user }
        else
          format.html { render :edit }
          format.json { render json: @user.errors, status: :unprocessable_entity }
        end
      end
    end

    # DELETE /users/1
    # DELETE /users/1.json
    def destroy
      @user.destroy
      respond_to do |format|
        format.html { redirect_to users_url, notice: 'User was successfully destroyed.' }
        format.json { head :no_content }
      end
    end

    # def get_postings_by_categories
    #   user = User.find(params[:user_id])
    #   @postings = user.postings.
    # end

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
