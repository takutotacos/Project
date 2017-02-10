json.action @action

json.status @postings.empty?? -1 : 1
unless @postings.empty? then
  json.postings @postings do |posting|
    json.id posting.id
    json.comment posting.comment
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