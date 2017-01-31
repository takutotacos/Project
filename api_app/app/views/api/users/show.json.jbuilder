json.type "SHOW"
json.status @user.present?? "1" : "-1"
json.users do |json|
  json.array!(@user) do |user|
    json.partial!(user)
  end
end
