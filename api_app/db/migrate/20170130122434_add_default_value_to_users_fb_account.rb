class AddDefaultValueToUsersFbAccount < ActiveRecord::Migration[5.0]
  def change
    change_column_default :users, :fb_account, '0'
  end
end
