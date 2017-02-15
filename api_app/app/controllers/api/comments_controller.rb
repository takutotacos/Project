require 'pry'
module Api
	class CommentsController < ApplicationController
		before_action :set_posting

		def create
			@comment = @posting.comments.build(comment_params)
			@comment.user_id = current_user.id
			@action = "CREATTE"
			@comment.save
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
	end
end