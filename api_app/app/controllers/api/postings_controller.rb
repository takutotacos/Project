require 'pry'
module Api
  class PostingsController < ApplicationController
  	def index
	  @postings = Posting.all
       render json: { status: @postings.present?? 1:-1 , postings: @postings }
  	end
  end
end
