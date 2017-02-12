json.action "5"
json.status @followers.empty?? -1: 1
# unless @followers.empty?
  json.followers @followers do |follower|
    json.id follower.id
    json.user_id follower.user_id
  end
# end
