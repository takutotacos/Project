json.type @type
json.status @status
if @status == 3
  json.user do |json|
    json.errors @user.errors if @status == 3
  end
else 
  json.partial!(@user) unless @user.nil?
end