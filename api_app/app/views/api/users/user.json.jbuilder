json.action @action
json.status @user.errors.empty?? 2 : 3
if @user.errors.present?
 json.errors @user.errors
else
 json.user do
  json.id @user.id
  json.user_id @user.user_id
  json.email @user.email
 end
end
