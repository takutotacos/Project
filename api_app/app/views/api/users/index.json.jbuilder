
if @user.present?
  json.post do |json|
    json.status "OK"
    json.user_id @user.user_id
    json.email @user.email
  end
end
