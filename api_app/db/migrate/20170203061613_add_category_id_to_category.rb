class AddCategoryIdToCategory < ActiveRecord::Migration[5.0]
  def change
  	add_column :categories, :category_id, :string
  end
end
