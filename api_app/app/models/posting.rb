class Posting < ApplicationRecord
  acts_as_mappable :default_units => :kms,
:lat_column_name => :latitude,
:lng_column_name => :longitude
  belongs_to :user
  belongs_to :category
  has_many :comments, dependent: :destroy
  has_many :likes, dependent: :destroy
  has_many :notifications, dependent: :destroy
  default_scope -> { order(created_at: :desc) }
  attr_accessor :can_like, :like_id

  def get_like_id(user_id)
    like = likes.find_by(user_id: user_id)
    @like_id = like.present?? like[:id] : nil
  end

  def user_can_like(user_id)
  	@can_like = likes.find_by(user_id: user_id).present?? false : true
  end
end
