json.extract! user, :id, :user_id, :email, :user_name, :password_digest, :icon, :icon_content_type, :fb_account, :created_at, :updated_at
json.url user_url(user, format: :json)