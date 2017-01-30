class ChangeColumnNullToUsers < ActiveRecord::Migration[5.0]
  def change
    change_column_null :users, :icon, true
    change_column_null :users, :icon_content_type, true
  end
end
