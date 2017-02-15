class Like < ApplicationRecord
	belongs_to :posting, counter_cache: :likes_count
	belongs_to :user
	validates_uniqueness_of :posting_id, :scope => :user_id
end
