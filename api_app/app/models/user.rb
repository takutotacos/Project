class User < ApplicationRecord
  has_many :postings, dependent: :destroy
  has_many :active_relationships, class_name: "Relationship",
  								 foreign_key: "follower_id",
  								 dependent: :destroy
  validates :user_id, :email, presence: true, uniqueness: { allow_blank: true }
  validates :password_digest, presence: true, allow_blank: true
  has_secure_password
  has_many :following, through: :active_relationships, source: :followed

  def follow(other_user)
  	active_relationships.create(followed_id: other_user.id)
  end

  def unfollow(other_user)
  	active_relationships.find_by(followed_id: other_user.id).destroy
  end

  def following?(other_user)
  	following.include?(other_user)
  end
end
