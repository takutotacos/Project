json.action @action
json.status @categories.empty?? -1 : 1
json.categories @categories do |category|
  json.category_name category.category_name
  json.id category.id
end