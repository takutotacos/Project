  json.users @followers do |user|
    json.id user.id
    json.user_id user.user_id
  end
