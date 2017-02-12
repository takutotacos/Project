json.status @user.postings.empty?? -1 : 1
unless @user.postings.empty? then
  json.user do
    json.id @user.id
    json.user_id @user.user_id
    json.postings @user.postings do |posting|
      json.id posting.id
      json.image posting.image
      json.comment posting.comment
      json.latitude posting.latitude
      json.longitude posting.longitude
      json.location1 posting.location1
      json.location2 posting.location2
      json.category do
        json.id posting.category.id
        json.category_name posting.category.category_name
      end
    end
  end
end
