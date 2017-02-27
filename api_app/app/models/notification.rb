class Notification < ApplicationRecord
  belongs_to :user
  belongs_to :notified_by, class_name: 'User'
  belongs_to :posting
  belongs_to :comment

  validates :user_id, :notified_by_id, :posting_id, :notice_type, presence: true
end
