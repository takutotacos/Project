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
      @postings = Posting.where('category_id = ? AND user_id IN (?)',
      params[:category_id], followings)
      @postings.each do |posting|
        posting.likes.build
        posting.user_can_like(current_user.id)
        posting.get_like_id(current_user.id)
      end
      @action = "get_postings_by_categories"
      render 'postings', formats: 'json', handlers: 'jbuilder'
    end

    private
    # Never trust parameters from the scary internet, only allow the white list through.
    def posting_params
      params.require(:posting).permit(:user_id, :image, :content, :latitude, :longitude,
      :address, :place_name, :place_category, :category_id)
    end
  end
end
