class AddActiveFlgToAllTables < ActiveRecord::Migration[5.0]
  def change
    tables = [:postings, :users, :categories]

    tables.each do |table_name|
      add_column table_name, :active_flg, :integer, default: 1
    end
  end
end
