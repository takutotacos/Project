json.action @action
json.status @category.errors.empty?? 2 : 3
if @category.errors.present?
  json.errors @category.errors
else
 json.category do
  json.id @category.id
  json.category_name @category.category_name
 end
end
