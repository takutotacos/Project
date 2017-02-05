class Category < ApplicationRecord
	has_many :postings
	validates :category_name, presence: true, uniqueness: true 
end
