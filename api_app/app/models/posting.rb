class Posting < ApplicationRecord
  belongs_to :user
  belongs_to :category
  has_many :comments, dependent: :destroy
  has_many :likes, dependent: :destroy
  default_scope -> { order(created_at: :desc) }

  def like_user(user_id)
  	likes.find_by(user_id: user_id)
  end
end
