json.type @type
json.status @postings.present?? "1" : "-1"
json.posting @postings do |posting|
  json.id posting.id
  json.user_id posting.user_id
  json.image posting.image
  json.comment posting.comment
  json.latitude posting.latitude
  json.longitude posting.longitude
  json.location1 posting.location1
  json.location2 posting.location2
  json.created_at posting.created_at
  json.updated_at posting.updated_at

  json.category posting.category_name
  json.user posting.user
end
