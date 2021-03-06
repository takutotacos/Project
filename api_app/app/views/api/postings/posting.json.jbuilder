json.id @posting.id
json.content @posting.content
json.image @posting.image
json.like_counts @posting.likes_count
json.comment_counts @posting.comments.count
json.can_like @posting.can_like
json.like_id @posting.like_id
json.user do
  json.id @posting.user.id
  json.user_id @posting.user.user_id
  json.email @posting.user.email
end
json.category do
  json.id @posting.category.id
  json.category_name @posting.category.category_name
end
if @posting.comments.present?
  json.comments @posting.comments do |comment|
    json.id comment.id
    json.content comment.content
    json.created_at comment.created_at
    json.user do
      json.id comment.user.id
      json.user_id comment.user.user_id
    end
  end
end
