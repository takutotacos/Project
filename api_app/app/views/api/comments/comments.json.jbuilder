json.comments @comments do |comment|
  json.id comment.id
  json.content comment.content
  json.created_at comment.created_at
  json.user_id comment.user_id
  json.user do
    json.id comment.user.id
    json.user_id comment.user.user_id
  end
end
