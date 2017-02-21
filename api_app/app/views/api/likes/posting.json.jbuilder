json.action @action
json.status @like.errors.empty?? 2 : 3
if @like.errors.present?
	json.errors @like.errors
else
	json.posting do
		json.id @posting.id
		json.user_id @posting.user_id
		json.like_counts @posting.likes.count
		json.comment_counts @posting.comments.count
	    json.can_like @posting.can_like
	    json.like_id @posting.like_id
	end
end
