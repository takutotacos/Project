require 'pry'
module Api
	class LikesController < ApplicationController
		before_action :set_posting

		def create
			# when hitting a like button just return the number of the likes?
			@action = "8"
			@like = @posting.likes.new(user_id: current_user.id)
			@like.save
			@likes = @posting.likes
			render 'posting', formats: 'json', handlers: 'jbuilder'
		end

		def destroy
			like = @posting.where(user_id: current_user.id)
			like.destroy
			@likes = @posting.likes
			render 'posting', formats: 'json', handlers: 'jbuilder'
		end

		private
	    def set_posting
	    		@posting = Posting.find(params[:posting_id])
	    	end
	end
end