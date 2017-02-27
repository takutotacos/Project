require 'pry'
module Api
	class CommentsController < ApplicationController
		before_action :set_posting

		def index
			@comments = @posting.comments
			render 'comments', formats: 'json', handlers: 'jbuilder'
		end

		def create
			@comment = @posting.comments.build(comment_params)
			@comment.user_id = current_user.id
			@action = "10"
			if @comment.save
				create_notification @posting, @comment
			end
			@posting.comments
			render 'comment', formats: 'json', handlers: 'jbuilder'
		end

		def edit
		end

		def destroy
		end

		private
	    # Never trust parameters from the scary internet, only allow the white list through.
	    def comment_params
	    		params.require(:comment).permit(:content)
	    end

	    def set_posting
    	# Todo is this the right way to refer to the posting_id?
    		@posting = Posting.find(params[:posting_id])
    	end

		def create_notification(posting, comment)
			return if posting.user.id == current_user.id
			Notification.create(user_id: posting.user.id, notified_by_id: current_user.id,
									posting_id: posting.id, comment_id: comment.id,
									notice_type: 'comment')
		end
	end
end
