class Posting < ApplicationRecord
  belongs_to :user
  belongs_to :category
  has_many :comments, dependent: :destroy
  default_scope -> { order(created_at: :desc) }
end
