json.action @action
json.status @comment.errors.empty?? 2 : 3
if @comment.errors.present?
	json.errors @comment.errors
else
	json.posting do
		json.id @posting.id
		json.user_id @posting.user_id
		json.comment_counts @posting.comments.count
	end
end
