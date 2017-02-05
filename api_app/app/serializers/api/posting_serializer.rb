module Api
  class PostingSerializer < ActiveModel::Serializer
    attributes :id, :comment, :image, :latitude, :longitude, :location1, :location2,
     :created_at, :updated_at, :category, :user

    has_one :user, serializer: UserSerializer
    has_one :category
  end
end
