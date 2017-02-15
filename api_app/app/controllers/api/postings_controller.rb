require 'pry'
module Api
  class PostingsController < ApplicationController

    def index
      @action = "INDEX"
      # @user = User.find(params[:user_id])
      # @postings = @user.postings
      @postings = current_user.postings
      render 'postings', formats: 'json', handlers: 'jbuilder'
    end

    def show
      @postings = Posting.find(params[:id])
      render json: @postings
    end

    def create
      @posting = Posting.new(posting_params)
      @action = "CREATE"
      if @posting.save
        @status = 2
        render 'posting', formats: 'json', handlers: 'jbuilder'
      else
        @status = 3
        render 'posting', formats: 'json', handlers: 'jbuilder'
      end
    end

    # 本当はユーザコントローラに書くほうがいいのかも
    # when retrieving data as a user's child elements
    def get_postings_by_categories
      followings = current_user.following.select("id")
      @postings = Posting.where(category_id: params[:category_id]) if followings.empty?
      @postings = Posting.where('category_id = ? AND user_id IN (?)',
      params[:category_id], followings) unless followings.empty?
      @action = "get_postings_by_categories"
      render 'postings', formats: 'json', handlers: 'jbuilder'
    end

    private
    # Never trust parameters from the scary internet, only allow the white list through.
    def posting_params
      params.require(:posting).permit(:user_id, :image, :comment, :latitude, :longitude,
      :location1, :location2, :category_id)
    end
  end
end
