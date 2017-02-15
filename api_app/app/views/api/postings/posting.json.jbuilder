json.action @action
json.status @status
if @status == 2 then
  json.posting do
    json.id @posting.id
    json.comment @posting.comment
    json.image @posting.image
    json.likes_count @posting.likes_count
    json.comments_count @posting.comments.count
    json.user do
      json.id @posting.user.id
      json.user_id @posting.user.user_id
      json.email @posting.user.email
    end
    json.category do
      json.id @posting.category.id
      json.category_name @posting.category.category_name
    end
  end
end
