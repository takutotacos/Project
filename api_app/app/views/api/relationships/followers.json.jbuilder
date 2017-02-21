json.action "5"
json.status @followers.empty?? -1: 1
  json.users @followers do |user|
    json.id user.id
    json.user_id user.user_id
  end
