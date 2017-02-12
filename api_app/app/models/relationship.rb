class Relationship < ApplicationRecord
	belongs_to :follower, class_name: "User"
	belongs_to :followed, class_name: "User"
	validates :follower_id, presence: true
	validates :followed_id, presence: true

	after_create :create_posting

	def create_posting
		followed.postings.create!(user_id: "system", comment: "#{follower.user_id}があなたをフォローしました。")
	end
end
