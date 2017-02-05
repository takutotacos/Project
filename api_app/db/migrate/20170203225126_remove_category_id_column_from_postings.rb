class RemoveCategoryIdColumnFromPostings < ActiveRecord::Migration[5.0]
  def change
    remove_column :postings, :category_id
  end
end
