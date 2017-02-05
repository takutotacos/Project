require 'pry'
module Api
  class PostingsController < ApplicationController
    def index
      @postings = Posting.all
      render json: @postings
      # render json: { status: @postings.present?? 1:-1, postings: @postings }
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

    private
    # Never trust parameters from the scary internet, only allow the white list through.
    def posting_params
      params.require(:posting).permit(:user_id, :image, :comment, :latitude, :longitude,
      :location1, :location2, :category_id)
    end
  end
end
