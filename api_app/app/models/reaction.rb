class Reaction < ApplicationRecord
  belongs_to :posting, counter_cache: :likes_count
  belongs_to :user
end
