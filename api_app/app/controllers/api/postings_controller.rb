require 'pry'
module Api
  class PostingsController < ApplicationController

    def index
      @user = User.where(user_id: parmas[user_id])
      @postings = @user.postings
      render json: @postings
      # render json: { status: @postings.present?? 1:-1, postings: @postings }
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
        render json: { action: @action, status: @status, posting: @posting }
      else
        @status = 3
        render json: { action: @action, status: @status }
      end
    end

    # 本当はユーザコントローラに書くほうがいいのかも
    def get_postings_by_categories
      category_id = params[:category_id]
      @postings = Posting.where(category_id: category_id)
      render json: { postings: @postings }
    end

    private
    # Never trust parameters from the scary internet, only allow the white list through.
    def posting_params
      params.require(:posting).permit(:user_id, :image, :comment, :latitude, :longitude,
      :location1, :location2, :category_id)
    end
  end
end
