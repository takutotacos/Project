class User < ApplicationRecord
  has_many :postings
  attr_accessor :password
  validates :user_id, :email, presence: true, uniqueness: { allow_blank: true }
  validates :password, presence: true, allow_blank: true
  has_secure_password
end
