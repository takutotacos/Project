json.action @action
json.status @postings.empty?? -1 : 1
unless @postings.empty? then
  json.postings @postings do |posting|
    json.id posting.id
    json.content posting.content
    json.image posting.image
    json.like_counts posting.likes_count
    json.comment_counts posting.comments.count
    json.can_like posting.can_like
    json.like_id posting.like_id
    json.latitude posting.latitude
    json.longitude posting.longitude
    json.user do
      json.id posting.user.id
      json.user_id posting.user.user_id
      json.email posting.user.email
    end
    json.category do
      json.id posting.category.id
      json.category_name posting.category.category_name
    end
  end
end
