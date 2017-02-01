class UserSerializer < ActiveModel::BaseSerializer
  attributes :id, :user_id, :email, :icon, :icon_file_type, :created_at, :updated_at
  has_many :postings
end
