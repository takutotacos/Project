module Api
  class UserSerializer < ActiveModel::Serializer
    attributes :id, :user_id, :email, :icon, :icon_content_type, :created_at, :updated_at, :postings
    has_many :postings
  end
end
