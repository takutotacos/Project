class AddLikesCountColumnToPostings < ActiveRecord::Migration[5.0]
  def change
  	add_column :postings, :likes_count, :integer, :default => 0
  end
end
