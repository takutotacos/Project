module Api
  class PostingSerializer < ActiveModel::Serializer
    attributes :id, :user_id, :comment, :image, :latitude, :longitude, :location1, :location2, :created_at, :updated_at, :category, :user

    has_many :users, serializer: UserSerializer
    belongs_to :category
  end
end