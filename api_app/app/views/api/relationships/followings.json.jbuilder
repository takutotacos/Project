json.action "4"
json.status @followings.empty?? -1 : 1
json.followings @followings do |following|
  json.id following.id
  json.user_id following.user_id
end
