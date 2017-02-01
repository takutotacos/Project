class User < ApplicationRecord
  has_many :postings
  validates :user_id, :email, presence: true, uniqueness: { allow_blank: true }
  validates :password_digest, presence: true, allow_blank: true
  has_secure_password
end
