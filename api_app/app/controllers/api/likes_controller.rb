require 'pry'
module Api
	class LikesController < ApplicationController
		before_action :set_posting
		before_action :set_like, :only => :destroy

		def create
			# when hitting a like button just return the number of the likes?
			@action = "8"
			@like = @posting.likes.new(user_id: current_user.id)
			if @like.save
				create_notification @posting
			end
			@posting.likes
			@posting.user_can_like(current_user.id)
			@posting.get_like_id(current_user.id)
			render 'posting', formats: 'json', handlers: 'jbuilder'
		end

		def destroy
			@action = "9"
			@like.destroy
			@posting.likes
			@posting.user_can_like(current_user.id)
			@posting.get_like_id(current_user.id)
			render 'posting', formats: 'json', handlers: 'jbuilder'
		end

		private
		def set_posting
			@posting = Posting.find(params[:posting_id])
		end

		def set_like
			@like = Like.find(params[:id])
		end

		def create_notification(posting)
			return if posting.user.id == current_user.id
			Notification.create(user_id: posting.user.id, notified_by_id: current_user.id,
									posting_id: posting.id, notice_type: 'like')
		end

	end
end
