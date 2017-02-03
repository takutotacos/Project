class AddCategoryIdToPostings < ActiveRecord::Migration[5.0]
  def change
  	add_column :postings, :category_id, :integer
  end
end
