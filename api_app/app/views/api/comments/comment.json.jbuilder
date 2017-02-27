json.id @posting.id
json.user_id @posting.user_id
json.comment_counts @posting.comments.count
json.comments @posting.comments do |comment|
  json.id comment.id
  json.content comment.content
  json.created_at comment.created_at
  json.user do
    json.id comment.user.id
    json.user_id comment.user.user_id
  end
end
