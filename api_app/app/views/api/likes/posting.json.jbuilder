json.action @action
json.status @like.errors.empty?? 2 : 3
if @like.errors.present?
	json.errors @like.errors
else
	json.posting do
		json.id @posting.id
		json.user_id @posting.user_id
		json.likes_count @posting.likes_count
		json.comment_count @posting.comments.count
	end
end