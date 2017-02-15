json.action @action
json.status @comment.errors.empty?? 2 : 3
if @comment.errors.present?
	json.errors @comment.errors
else
	json.comment do
		json.id @comment.id
		json.content @comment.content
		json.user_id @comment.user_id
		json.posting_id @comment.posting_id
	end
end