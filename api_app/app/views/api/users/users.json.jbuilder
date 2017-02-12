json.action @action
json.status @users.empty?? -1 : 1
json.users @users do |user|
  json.id user.id
  json.user_id user.user_id
end
